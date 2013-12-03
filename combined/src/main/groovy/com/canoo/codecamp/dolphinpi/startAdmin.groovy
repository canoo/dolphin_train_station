package com.canoo.codecamp.dolphinpi

import com.canoo.codecamp.dolphinpi.admin.AdminApplication
import javafx.application.Application

AdminApplication.clientDolphin = new ConfigProvider().newConfig().getClientDolphin();

Application.launch(AdminApplication.class);


