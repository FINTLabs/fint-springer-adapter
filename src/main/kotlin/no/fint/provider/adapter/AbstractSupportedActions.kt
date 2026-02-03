package no.fint.provider.adapter

abstract class AbstractSupportedActions {
    private val actions: MutableList<String> = mutableListOf()

    fun getActions(): List<String> = actions

    fun add(e: Enum<*>) {
        actions.add(e.name)
    }

    fun add(name: String) {
        actions.add(name)
    }

    fun addAll(e: Class<out Enum<*>>) {
        e.enumConstants?.map(Enum<*>::name)?.forEach(actions::add)
    }

    fun supports(action: String): Boolean = actions.contains(action)
}
