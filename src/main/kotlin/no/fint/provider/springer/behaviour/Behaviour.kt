package no.fint.provider.springer.behaviour

import no.fint.event.model.Event
import no.fint.event.model.Problem
import java.util.*

fun interface Behaviour<T> {
    fun accept(event: Event<*>, payload: T)

    fun addProblem(event: Event<*>, field: String, message: String) {
        if (event.problems == null) {
            event.problems = ArrayList()
        }
        val problem = Problem().apply {
            this.field = field
            this.message = message
        }
        event.problems.add(problem)
    }

    fun empty(list: List<*>?): Boolean = list.isNullOrEmpty()
}
