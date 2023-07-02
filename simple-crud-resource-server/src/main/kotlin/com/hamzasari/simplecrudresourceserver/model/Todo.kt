package com.hamzasari.simplecrudresourceserver.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import lombok.Data
import lombok.NoArgsConstructor
import lombok.Value

@Entity
@Data
@NoArgsConstructor
class Todo {
    @Id
    @GeneratedValue
    private var id: Long? = null
    private var title: String = ""
    private var completed = false

    fun setTitle(value: String) {
        title = value
    }
    fun setCompleted(value: Boolean) {
        completed = value
    }
    fun getTitle(): String {
        return title
    }
    fun getCompleted(): Boolean {
        return completed
    }

    override fun toString(): String {
        return "Todo(id=$id, title='$title', completed=$completed)"
    }
}