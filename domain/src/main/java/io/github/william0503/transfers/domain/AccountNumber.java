package io.github.william0503.transfers.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public record AccountNumber(String branch, String number) {

    private static final Pattern BRANCH = Pattern.compile("\\d{4}");
    private static final Pattern NUMBER = Pattern.compile("\\d{5}-\\d");

    public AccountNumber {
        Objects.requireNonNull(branch, "branch is required");
        Objects.requireNonNull(number, "number is required");
        if (!BRANCH.matcher(branch).matches()) {
            throw new IllegalArgumentException("branch must have 4 digits: " + branch);
        }
        if (!NUMBER.matcher(number).matches()) {
            throw new IllegalArgumentException("number must look like 12345-6: " + number);
        }
    }

    @Override
    public String toString() {
        return branch + "/" + number;
    }
}