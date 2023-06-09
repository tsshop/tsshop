package com.shop.tsshop.web.netty.attribute;

import com.shop.tsshop.web.netty.session.Session;
import io.netty.util.AttributeKey;

/**
 * @Desc :
 * @Create : tsshop ~ 2020/06/13
 */
public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
