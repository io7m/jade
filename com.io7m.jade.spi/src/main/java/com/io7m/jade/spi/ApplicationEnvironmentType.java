/*
 * Copyright © 2020 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jade.spi;

import java.nio.file.FileSystem;
import java.util.Iterator;
import java.util.Optional;

/**
 * An abstraction over the application's execution environment.
 */

public interface ApplicationEnvironmentType
{
  /**
   * @return The application's filesystem
   */

  FileSystem filesystem();

  /**
   * Retrieve the value of a system property.
   *
   * @param name The property name
   *
   * @return The property value
   */

  Optional<String> systemProperty(
    String name
  );

  /**
   * Retrieve the value of an environment variable.
   *
   * @param name The variable name
   *
   * @return The variable value
   */

  Optional<String> environmentVariable(
    String name
  );

  /**
   * @param clazz The service class
   * @param <S>   The service type
   *
   * @return Available services of type {@code S}
   */

  <S> Iterator<S> servicesFor(
    Class<S> clazz
  );
}
