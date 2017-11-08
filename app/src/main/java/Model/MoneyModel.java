package Model;

/**
 * Created by Shailendra on 12/1/2016.
 */
public class MoneyModel {

    public String IEType;
    public String description;
    public long amount;
    public String date;
    public String category;
    public int amount2;
    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    private int primaryKey;

    public String getIEType() {
        return IEType;
    }

    public void setIEType(String IEType) {
        this.IEType = IEType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
