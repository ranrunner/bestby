package com.ranrunner.bestby;

import android.content.Intent;
import android.widget.RemoteViewsService;

// method that provides factory to be bound to collection service
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ItemViewsFactory(this.getApplicationContext(), intent);
    }
}