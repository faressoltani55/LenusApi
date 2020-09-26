package com.osc.lenus.LenusBackend.payload.responses;

import com.osc.lenus.LenusBackend.payload.utilities.Occupation;
import com.osc.lenus.LenusBackend.model.local.Recommandation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MobileHomePageData {
    private List<Occupation> occupations;
    private List<Recommandation> recommandations;
}
