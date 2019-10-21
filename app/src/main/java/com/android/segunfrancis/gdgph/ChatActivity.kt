package com.android.segunfrancis.gdgph

import ai.api.AIListener
import ai.api.AIServiceException
import ai.api.android.AIConfiguration
import ai.api.android.AIDataService
import ai.api.android.AIService
import ai.api.model.AIError
import ai.api.model.AIRequest
import ai.api.model.AIResponse
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.segunfrancis.gdgph.adapter.ChatAdapter
import com.android.segunfrancis.gdgph.model.Chat
import com.google.android.gms.tasks.Task
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity(), AIListener {

    private lateinit var mEditText: EditText
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var fab: ExtendedFloatingActionButton
    private lateinit var toolbar: MaterialToolbar
    private lateinit var mAIService: AIService
    private lateinit var mReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mList: List<Chat>
    private lateinit var mChatAdapter: ChatAdapter
    private lateinit var mProgressBar: ProgressBar
    private var clientAccessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mAuth = FirebaseAuth.getInstance()

        toolbar = findViewById(R.id.toolbar)
        mProgressBar = findViewById(R.id.progress_bar_chat)
        mRecyclerView = findViewById(R.id.chat_recycler_view)
        mEditText = findViewById(R.id.messageEditText)
        fab = findViewById(R.id.floatingActionButton)

        mProgressBar.visibility = View.VISIBLE
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        mList = ArrayList<Chat>()

        mRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = layoutManager
        mReference = FirebaseDatabase.getInstance().reference
        mReference.keepSynced(true)

        mReference.child("clientToken").child("clientAccessToken")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    clientAccessToken = dataSnapshot.value.toString()
                    Log.d(TAG, "ClientAccessToken: $clientAccessToken")
                }

                override fun onCancelled(dataSnapshot: DatabaseError) {

                }
            })

        mReference.child(mAuth.uid.toString()).child("chat")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    (mList as ArrayList<Chat>).clear()
                    for (snapshot: DataSnapshot in dataSnapshot.children) {
                        val message: Chat? = snapshot.getValue(
                            Chat::class.java)
                        (mList as ArrayList<Chat>).add(message!!)
                    }
                    mChatAdapter = ChatAdapter(this@ChatActivity, mList)
                    mRecyclerView.adapter = mChatAdapter
                    mRecyclerView.scrollToPosition(mList.size - 1)
                    mProgressBar.visibility = View.GONE
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })

        val config = AIConfiguration(
            "dea3393a7fe04d6b878d2dabddb6be70",
            ai.api.AIConfiguration.SupportedLanguages.English,
            AIConfiguration.RecognitionEngine.System
        )

        mAIService = AIService.getService(this, config)
        mAIService.setListener(this)

        val aiDataService = AIDataService(this, config)
        val aiRequest = AIRequest()

        fab.setOnClickListener {
            val message = mEditText.text.toString().trim()
            if (!TextUtils.isEmpty(message)) {
                val chatMessage = Chat(message, "user")
                mReference.child(mAuth.uid.toString()).child("chat").push().setValue(chatMessage)

                aiRequest.setQuery(message)
                class Task : AsyncTask<AIRequest, Void, AIResponse>() {
                    override fun doInBackground(vararg aiRequests: AIRequest): AIResponse? {
                        //val mRequest = aiRequests[0]
                        try {
                            return aiDataService.request(aiRequest)
                        } catch (e: AIServiceException) {
                            Log.e(TAG, "doInBackground: AIResponse: ${e.message}")
                        }
                        return null
                    }

                    override fun onPostExecute(aiResponse: AIResponse?) {
                        if (aiResponse != null) {
                            val result = aiResponse.result
                            val reply = result.fulfillment.speech
                            val messageReply =
                                Chat(reply, "bot")
                            mReference.child(mAuth.uid.toString())
                                .child("chat").push().setValue(messageReply)
                        }
                    }
                }
                Task().execute(aiRequest)
            }
            mEditText.setText("")
        }
    }

    override fun onResult(response: AIResponse?) {
        val result = response?.result
        val message = result!!.resolvedQuery
        val chatMessage = Chat(message, "user")
        mReference.child(mAuth.uid.toString()).child("chat")
            .push().setValue(chatMessage)

        val reply = result.fulfillment.speech
        val chatMessage1 = Chat(reply, "bot")
        mReference.child(mAuth.uid.toString()).child("chat")
            .push().setValue(chatMessage1)
    }

    override fun onListeningStarted() {

    }

    override fun onAudioLevel(level: Float) {

    }

    override fun onError(error: AIError?) {

    }

    override fun onListeningCanceled() {

    }

    override fun onListeningFinished() {

    }


    /* **************************** MENU ********************************** */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.clear_chat) {

            val alertDialog = MaterialAlertDialogBuilder(this)
                .setTitle("All chats will be cleared")
                .setPositiveButton("YES") { dialogInterface, i ->
                    clearChat()
                    dialogInterface.dismiss()
                }
                .setNegativeButton("NO") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
            alertDialog.create().show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun clearChat() {
        mReference.child(mAuth.uid.toString())
            .child("chat")
            .removeValue()
            .addOnCompleteListener { task: Task<Void> ->
                if (task.isSuccessful) {
                    Snackbar.make(fab, "Deleted", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(fab, "ERROR: ${task.exception}", Snackbar.LENGTH_LONG).show()
                    Log.e(TAG, "ERROR: ${task.exception}")
                }
            }
    }

    companion object {
        private const val TAG = "ChatActivity"
    }
}
