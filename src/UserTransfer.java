public class UserTransfer {
    private int p_account_id;

    private String email;

    private String passwort;

    private String nickname;

    public UserTransfer(int p_account_id, String email, String passwort, String nickname){
        this.p_account_id = p_account_id;
        this.email = email;
        this.passwort = passwort;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return String.format("{p_account_id:%d;email:%s;passwort:%s;nickname:%s}", p_account_id, email, passwort, nickname);
    }

    public void setP_account_id(int p_account_id) {
        this.p_account_id = p_account_id;
    }
    public int getP_account_id() {
        return p_account_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
    public String getPasswort() {
        return passwort;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }

}
