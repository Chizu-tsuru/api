package com.chizu.tsuru.api.core.useCases;

public interface UseCase<Type, Params> {
    Type execute(Params params);
}
