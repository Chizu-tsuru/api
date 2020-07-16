package com.chizu.tsuru.map_clustering.core.useCases;

public interface UseCase<Type, Params> {
    Type execute(Params params);
}
