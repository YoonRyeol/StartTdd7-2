class StubWeakPasswordChecker: WeakPasswordChecker {
    var weak = false
    override fun checkPasswordWeak(pw: String): Boolean {
        return weak
    }
}
