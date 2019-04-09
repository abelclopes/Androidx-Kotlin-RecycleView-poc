package eti.com.abellopes.repository

import eti.com.abellopes.repository.model.Herois

class ItemsRepository {

    private var nextItem = 1

    fun getItemsPage(pageSize: Int = 10): List<Herois> {

        val items = mutableListOf<Herois>()
        val lastItem = nextItem + pageSize - 1

        for (i in nextItem..lastItem) {
            items.add(
                Herois(
                    id = i,
                    name = "Heroi $i",
                    description = "Heroi description $i",
                    loading = ""
                )
            )
        }

        nextItem = lastItem + 1

        return items
    }
}