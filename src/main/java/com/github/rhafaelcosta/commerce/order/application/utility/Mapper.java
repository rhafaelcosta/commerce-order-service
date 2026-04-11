package com.github.rhafaelcosta.commerce.order.application.utility;

public interface Mapper {
    <T> T convert(Object object, Class<T> destinationType);
}
