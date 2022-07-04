class MemoryUserRepository : UserRepository {
    private val users = mutableMapOf<String, User>()
    override fun save(user: User) {
        users[user.id] = user
    }

    override fun findById(id: String) = users[id]
}
