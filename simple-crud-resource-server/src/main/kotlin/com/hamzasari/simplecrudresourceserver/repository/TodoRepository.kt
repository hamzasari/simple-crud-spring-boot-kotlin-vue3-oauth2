package com.hamzasari.simplecrudresourceserver.repository

import com.hamzasari.simplecrudresourceserver.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource


@RepositoryRestResource
interface TodoRepository : JpaRepository<Todo?, Long?>