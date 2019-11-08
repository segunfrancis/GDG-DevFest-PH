package com.android.segunfrancis.gdgph.ui.organisers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.segunfrancis.gdgph.R
import com.android.segunfrancis.gdgph.adapter.OrganisersAdapter
import com.android.segunfrancis.gdgph.model.Organisers

class OrganisersFragment : Fragment() {

    private lateinit var organisersViewModel: OrganisersViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrganisersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        organisersViewModel =
            ViewModelProviders.of(this).get(OrganisersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_organisers, container, false)
        val organisers: ArrayList<Organisers> = ArrayList()
        val gino = Organisers("Gino Osahon", "GDG CLoud Port Harcourt", "https://images.unsplash.com/photo-1531299983330-093763e1d963?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=700&q=60"/*"https://drive.google.com/file/d/16rBEIsne0MTHz1dckdKrgi_Y9xkJIKP5/view?usp=sharing"*/)
        val philip = Organisers("Philip Obiorah", "GDG CLoud Port Harcourt", "https://images.unsplash.com/photo-1506634572416-48cdfe530110?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=700&q=60")
        val gloria = Organisers("Gloria Ojukwu", "GDG CLoud Port Harcourt", "https://images.unsplash.com/photo-1495994132590-5c7b6c4fffec?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=700&q=60")
        val albert = Organisers("Albert Amas", "GDG Port Harcourt", "https://images.unsplash.com/photo-1518882570151-157128e78fa1?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=700&q=60")
        val godfrey = Organisers("Godfrey Ayaosi", "GDG Port Harcourt", "https://images.unsplash.com/photo-1473107522457-4ed378e0c05f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=700&q=60")
        val esther = Organisers("Esther Itolima", "Women Tech Makers Port Harcourt", "https://images.unsplash.com/photo-1458681407517-f6a21aad9ec9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=700&q=60")

        organisers.add(gino)
        organisers.add(philip)
        organisers.add(gloria)
        organisers.add(albert)
        organisers.add(godfrey)
        organisers.add(esther)

        recyclerView = root.findViewById(R.id.organisers_recycler_view)

        adapter = OrganisersAdapter(organisers, root.context)
        recyclerView.layoutManager = GridLayoutManager(root.context, 2)
        recyclerView.adapter = adapter
        organisersViewModel.text.observe(this, Observer {

        })
        return root
    }
}