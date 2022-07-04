import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class UserRegisterTest {
    private lateinit var userRegister: UserRegister
    private val fakeRepository =  MemoryUserRepository()
    private val stubPasswordChecker = StubWeakPasswordChecker()
    private val spyEmailNotifier = SpyEmailNotifier()

    @BeforeEach
    fun setUp(){
        userRegister = UserRegister(stubPasswordChecker, fakeRepository, spyEmailNotifier)
    }

    @DisplayName("약한 암호면 가입 실패")
    @Test
    fun weakPassword() {
        stubPasswordChecker.weak = true
        assertThrows<WeakPasswordException> {
            userRegister.register("id", "pw", "email")
        }
    }

    @DisplayName("이미 같은 ID가 존재하면 가입 실패")
    @Test
    fun dupIdExists(){
        //given
        fakeRepository.save(User("id", "pw1", "email@email.com"))
        //when, then
        assertThrows<DupIdException> {
            userRegister.register("id", "pw2", "email")
        }
    }

    @DisplayName("가입하면 메일을 전송함")
    @Test
    fun whenRegisterThenSendMail(){
        userRegister.register("id", "pw", "email@email.com")
        assertTrue(spyEmailNotifier.called)
        assertEquals("email@email.com", spyEmailNotifier.email)
    }


}
