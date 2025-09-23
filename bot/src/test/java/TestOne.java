import com.lfj.datacontroller.PlayerData;
import org.junit.jupiter.api.Test;

import static com.lfj.plugin.telegrambot.algorithm.Enumeration.enumerationElements;
import org.telegram.telegrambots.meta.api.objects.User;

public class TestOne {
    @Test
    void enumerationAlgoTest(){
        User user = User
                .builder()
                .id(1512512512L)
                .firstName("LOL")
                .isBot(false)
                .build();
        String text = "/add_me Leo_Forter_Jalis leo-forter-jalis true #E00B0B";
        String[] args = text.split(" ");
        PlayerData data = new PlayerData();
        enumerationElements(args, data, user, null);
        System.out.printf("UserName >> %s, SendMessage >> %b, HexCode >> %s", data.getDisplayName(), data.getSendCodeVerification(), data.getHexCode());
    }
}
