package eti.com.abellopes.repository

import eti.com.abellopes.repository.model.Heroi

class ItemsRepository {

    private var nextItem = 1



    fun getItemsPage(pageSize: Int = 10): List<Heroi> {

        val items = mutableListOf<Heroi>()
        val lastItem = nextItem + pageSize - 1

        for (i in nextItem..lastItem) {
            items.add(
                Heroi(
                    id = i,
                    name = "Heroi $i",
                    description = "Heroi description $i",
                    picture = "",
                    loading = ""
                )
            )
        }

        nextItem = lastItem + 1

        return items
    }
}