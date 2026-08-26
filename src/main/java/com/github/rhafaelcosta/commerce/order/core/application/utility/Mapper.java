package com.github.rhafaelcosta.commerce.order.core.application.utility;

public interface Mapper {
    <T> T convert(Object object, Class<T> destinationType);
}
