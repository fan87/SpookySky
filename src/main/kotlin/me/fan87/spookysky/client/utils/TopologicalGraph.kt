package me.fan87.spookysky.client.utils

/**
 * Uh ok, let's be real, this is not a topological graph lol
 * Well, we are not sorting a very complicated large scaled stuff, so let's not worry about that
 */
class TopologicalGraph<NodeType> {
    private val input = HashMap<NodeType, Node<NodeType>>()
    private val cachedNodes = HashMap<NodeType, Node<NodeType>>()

    var stack = ArrayList<Node<NodeType>>()

    fun add(item: NodeType, before: List<NodeType>, after: List<NodeType>) {
        if (cachedNodes.containsKey(item)) {
            cachedNodes[item]!!.mustAfter
                .addAll(after.map { cachedNodes.getOrDefault(it, Node(this, ArrayList(), ArrayList(), it)) })
            cachedNodes[item]!!.mustBefore
                .addAll(before.map { cachedNodes.getOrDefault(it, Node(this, ArrayList(), ArrayList(), it)) })

        } else {
            cachedNodes[item] = Node(
                this,
                ArrayList(after.map { cachedNodes.getOrDefault(it, Node(this, ArrayList(), ArrayList(), it)) }),
                ArrayList(before.map { cachedNodes.getOrDefault(it, Node(this, ArrayList(), ArrayList(), it)) }),
                item)

        }
        input[item] = cachedNodes[item]!!
    }

    private fun visit(node: Node<NodeType>, stack: ArrayList<Node<NodeType>>) {
        node.visited = true
        stack.add(node)
    }

    fun calculate(): List<NodeType> {
        stack = ArrayList()

        while (!input.all { it.value.visited }) {
            for (node in input.values) {
                if (!node.visited) {
                    if (node.getAfters().map { original -> input.values.first { original.value.toString() == it.value.toString() } }.all { it.visited }) {
                        visit(node, stack)
                    }
                }
            }
        }



        return stack.map { it.value }
    }

    class Node<NodeType>(val graph: TopologicalGraph<NodeType>,
                         var mustAfter: ArrayList<Node<NodeType>>,
                         var mustBefore: ArrayList<Node<NodeType>>,
                         val value: NodeType
    ) {

        var visited: Boolean = false

        fun getAfters(): List<Node<NodeType>> {
            val out = ArrayList(mustAfter)
            for (item in graph.input) {
                if (item.value.mustBefore.any { it.value == this.value }) {
                    out.add(item.value)
                }
            }
            return out.filter {
                it.value.toString() != this.value.toString()
            }
        }

        fun getBefores(): List<Node<NodeType>> {
            val out = ArrayList(mustBefore)
            for (item in graph.input) {
                if (item.value.mustAfter.any { it.value == this.value }) {
                    out.add(item.value)
                }
            }
            return out.filter {
                it.value.toString() != this.value.toString()
            }
        }

    }

}