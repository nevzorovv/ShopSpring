package ru.vnevzorov.Shop.ApplicationEvent;

import org.springframework.context.ApplicationEvent;
import ru.vnevzorov.Shop.model.user.AbstractUser;

import java.util.Locale;
import java.util.Objects;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private AbstractUser abstractUser;

    public OnRegistrationCompleteEvent(String appUrl, Locale locale, AbstractUser abstractUser) {
        super(abstractUser);

        this.appUrl = appUrl;
        this.locale = locale;
        this.abstractUser = abstractUser;
    }

    @Override
    public String toString() {
        return "OnRegistrationCompleteEvent{" +
                "appUrl='" + appUrl + '\'' +
                ", locale=" + locale +
                ", abstractUser=" + abstractUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnRegistrationCompleteEvent that = (OnRegistrationCompleteEvent) o;
        return Objects.equals(appUrl, that.appUrl) &&
                Objects.equals(locale, that.locale) &&
                Objects.equals(abstractUser, that.abstractUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUrl, locale, abstractUser);
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public AbstractUser getAbstractUser() {
        return abstractUser;
    }

    public void setAbstractUser(AbstractUser abstractUser) {
        this.abstractUser = abstractUser;
    }
}
