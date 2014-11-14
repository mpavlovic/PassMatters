package eu.asyncro.passmatters.data;

import com.dmacan.lightandroid.type.LightData;
import com.google.gson.annotations.Expose;

/**
 * Created by ahuskano on 11/9/2014.
 */
public class DataAccount extends LightData{

    @Expose
    private String name;

    @Expose
    private int id;

    public DataAccount(int id,String name ) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
