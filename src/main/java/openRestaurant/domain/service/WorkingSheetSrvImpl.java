package openRestaurant.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkingSheetSrvImpl implements WorkingSheetSrv {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkingSheetSrvImpl.class);

    private final WorkingDaysSrv workingDaysSrv;

    private final WorkingHoursSrv workingHoursSrv;

    public WorkingSheetSrvImpl(@Autowired WorkingDaysSrv workingDaysSrv, @Autowired WorkingHoursSrv workingHoursSrv) {
        this.workingDaysSrv = workingDaysSrv;
        this.workingHoursSrv = workingHoursSrv;
    }


    @Override
    public WorkingDaysSrv getWorkingDaysSrv() {
        return this.workingDaysSrv;
    }

    @Override
    public WorkingHoursSrv getWorkingHoursSrv() {
        return this.workingHoursSrv;
    }
}
