package com.canoo.codecamp.dolphinpi

/**
 * Created with IntelliJ IDEA.
 * User: vladislav
 * Date: 03.09.13
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
class AdminActionsTests extends GroovyTestCase{

    void testReadFileFromClasspath(){
        def var = AdminActions.loadDepartureDTOs()
        assert 134 == var.size()

    }

}
