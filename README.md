# ZIO Mock Failed? ScalaMock to the Rescue!

> _"ZIO Mock не может — ScalaMock поможет!"_ (Russian talk title)

Source code examples for the [F[Scala] 2025](https://yandex.ru/project/verticals/fscala-2025) presentation on using ScalaMock with ZIO applications.

## Overview

This repository contains a step-by-step guide to building the `scalamock-zio` library from scratch. The examples showcase the iterative process of creating a seamless integration between ScalaMock and ZIO's dependency injection system, revealing the challenges encountered along the way and how they are solved.

## Project Structure

The repository is organized into three main modules:

```
scalamock-zio-talk/
├── scala2/          # Scala 2.13 examples with ZIO Mock
├── scala3/          # Scala 3 examples with ScalaMock progression
└── macros/          # Custom macro implementations for ScalaMock integration with ZIO
```

### Scala 2 Module (`scala2/`)

Contains examples using the `zio-mock` library with Scala 2.13:
- `ApiService.scala` / `UserService.scala` - Simple service implementations
- `ApiServiceZioMockSpec.scala` - Testing with ZIO Mock using the `@mockable` annotation

### Scala 3 Module (`scala3/`)

A step-by-step progression through building the `scalamock-zio` integration from scratch. The test files are numbered from `01` to `12`, each one building upon the previous and solving problems discovered along the way:
- Starting with basic mock usage outside ZIO layers
- Discovering and fixing test isolation issues
- Implementing proper expectation verification
- Extracting reusable patterns
- Creating the `zioMock` helper with custom macros

### Macros Module (`macros/`)

Custom Scala 3 macro implementations for ScalaMock integration with ZIO:
- `ZIOMock.scala` - Macro for creating ZIO Layer from ScalaMock mocks
- `DebugMock.scala` - Debug utilities for mock inspection

## Running the Examples

Run all tests:
```bash
sbt test
```

Run tests for a specific module:
```bash
sbt scala2/test
sbt scala3/test
```

Run a specific test:
```bash
sbt "scala3/testOnly ApiService12FixZioMockSpec"
```

## License

BSD Zero Clause License (0BSD)

Copyright (c) 2025 Evgeny Veretennikov

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH
REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM
LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR
OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
PERFORMANCE OF THIS SOFTWARE.
