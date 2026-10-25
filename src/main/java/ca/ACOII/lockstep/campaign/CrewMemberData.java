package ca.ACOII.lockstep.campaign;

import java.util.List;
import java.util.Map;

public record CrewMemberData(String id, String name, int releaseDate, List<String> gear, Map<String, Integer> skillMap) {}
