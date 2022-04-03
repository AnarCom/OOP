package thread;

import lombok.Getter;
import lombok.Setter;

public class FiniteThread extends Thread {
    @Getter
    private boolean isCont = true;

    public void breakWork(){
        isCont = false;
        this.interrupt();
    }
}
