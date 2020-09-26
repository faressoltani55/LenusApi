package com.osc.lenus.LenusBackend.payload.responses;

import com.osc.lenus.LenusBackend.payload.utilities.Occupation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DashboardData {
    private List<Occupation> occupations;

}
