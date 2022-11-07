package dev.krysztal.advagri.event;

import dev.krysztal.advagri.api.event.DayChangeEvent;

public class AdvAgriEvents {

  public static void init() {
    //============================Server world load event===================//
    //    ServerWorldEvents.LOAD.register(WorldLoadEvent.enableAdvAgriSolarTerm);
    //============================Server world unload event=================//
    //    ServerWorldEvents.UNLOAD.register(WorldUnloadEvent.disableAdvAgriSolarTerm);
    //============================Day change before event===================//
    DayChangeEvent.BEFORE.register(DayChangeEvents.afterDayChange);
    //============================Day change after event====================//

  }
}
