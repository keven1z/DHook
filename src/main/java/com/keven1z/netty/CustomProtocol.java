
package com.keven1z.netty;

import java.io.Serializable;

/**
 * @author keven1z
 * @date 2021/12/23
 */
public class CustomProtocol implements Serializable {

    private static final long serialVersionUID = 4671171056512301542L;
    private String id;
    private int action = 0;
    private String body = "";

    public CustomProtocol() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAction() {
        return action;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
