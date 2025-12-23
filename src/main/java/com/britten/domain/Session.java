package com.britten.domain;

import java.util.List;

public record Session(SessionType sessionType, String sessionName, int sessionKey) {
}
