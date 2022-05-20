package test;
import guc.bttsBtngan.authentication.AuthenticationMain;
import guc.bttsBtngan.authentication.service.AuthService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = AuthenticationMain.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthenticationTests {

    @Autowired
    private AuthService authService;

    @Test
    public void validLoginTest(){
        String validUserName = "Mazenelgamed";
        String validPassword = "12345";
        try {
          String token =  authService.login(validUserName, validPassword);
          Assert.assertNotNull(token);
        }
        catch(Exception e){
        }

    }
    @Test
    public void inValidLoginTest(){
        String validUserName = "Mazenelgamed";
        String validPassword = "12346";
        try {
            String token =  authService.login(validUserName, validPassword);
        }
        catch(Exception e){
            System.out.println("An error occured: " +e.getMessage());
            Assert.assertEquals("INVALID_CREDENTIALS",e.getMessage());
        }

    }

    @Test
    public void validVerifyTest(){
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYXplbmVsZ2FtZWQiLCJpYXQiOjE2NTMwNTg4Njl9.hScZH1QlkvbDFKfRuwZD2MsqYdOUayichsRa9mdfEUO95n8JaO1yN3pGJ80k9wuoYfTFQVQQQy4E_CaaPH0shA";
        try{
            String userId = authService.verify(token);
            Assert.assertEquals("402881f480e1f1060180e1f1a9380000", userId);
        }
        catch(Exception e){
        }
    }

    @Test
    public void inValidVerifyTest(){
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYXplbmVsZ2FtZWQiLCJpYXQiOjE2NTMwNTkzOTV9.IZilumR2S599OrNo7klqu9mCc5loGYhbP0HjH_hF8iPWIeQGuyY7-JSB95ZYk3hgOrAsvp-gge5CWW1w95i5-A";
        try {
            String userId = authService.verify(token);
        }catch(Exception e){
            Assert.assertEquals("token is not in cache", e.getMessage());
        }

    }
    @Test
    public void validLogoutTest(){
        try {
            String token = authService.login("Mazenelgamed", "12345");
            String response = authService.logout(token);
            Assert.assertEquals("logged out successfully",response);
        }
        catch(Exception e){
        }
    }

    @Test
    public void inValidLogoutTest(){
        try{
            String token = authService.login("Mazenelgamed", "12345");
            authService.logout(token);
            authService.logout(token);
        }
        catch (Exception e){
            Assert.assertEquals("token is not in cache", e.getMessage());
        }
    }


}
