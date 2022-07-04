class SpyEmailNotifier : EmailNotifier {
    var called = false
    lateinit var email: String
    override fun sendRegisterEmail(email: String) {
        called = true
        this.email = email
    }
}
