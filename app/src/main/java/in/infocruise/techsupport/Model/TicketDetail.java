package in.infocruise.techsupport.Model;

public class TicketDetail {
    private String mId;
    private String mTicketNote;
    private String mContact;
    private String mDealerCode;
    private String mUser;
    private String mCreatedTime;
    private String mTag;

    public TicketDetail(String mId, String mTicketNote, String mContact, String mDealerCode, String mUser, String mCreatedTime, String mTag) {
        this.mId = mId;
        this.mTicketNote = mTicketNote;
        this.mContact = mContact;
        this.mDealerCode = mDealerCode;
        this.mUser = mUser;
        this.mCreatedTime = mCreatedTime;
        this.mTag = mTag;
    }

    public TicketDetail(String mTicketNote,String mContact,String mDealerCode){
        this.mTicketNote = mTicketNote;
        this.mContact = mContact;
        this.mDealerCode = mDealerCode;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getTicketNote() {
        return mTicketNote;
    }

    public void setTicketNote(String mTicketNote) {
        this.mTicketNote = mTicketNote;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String mContact) {
        this.mContact = mContact;
    }

    public String getDealerCode() {
        return mDealerCode;
    }

    public void setDealerCode(String mDealerCode) {
        this.mDealerCode = mDealerCode;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String mUser) {
        this.mUser = mUser;
    }

    public String getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(String mCreatedTime) {
        this.mCreatedTime = mCreatedTime;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String mTag) {
        this.mTag = mTag;
    }
}