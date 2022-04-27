package com.epam.esm.model.query;

import java.util.ArrayList;
import java.util.Map;

public interface QueryBuilder {

    String buildQuery(String sql, Map<String, String> parameters);

    ArrayList<String> extractQueryArguments(Map<String, String> parameters);

}
