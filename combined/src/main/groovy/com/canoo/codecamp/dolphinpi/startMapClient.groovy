package com.canoo.codecamp.dolphinpi

import com.canoo.codecamp.dolphinpi.VladBoard.VladBoardApplication
import javafx.application.Application

VladBoardApplication.clientDolphin = new ConfigProvider().newConfig().getClientDolphin();

Application.launch(VladBoardApplication.class);


