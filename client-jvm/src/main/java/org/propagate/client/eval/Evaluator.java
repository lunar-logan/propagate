package org.propagate.client.eval;

import org.propagate.common.domain.util.Either;

public interface Evaluator {
    Either<String> eval();
}
