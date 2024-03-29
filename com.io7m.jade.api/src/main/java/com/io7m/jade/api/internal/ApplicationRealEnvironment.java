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

package com.io7m.jade.api.internal;

import com.io7m.jade.spi.ApplicationEnvironmentType;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 * An application environment based on the real system environment.
 */

public final class ApplicationRealEnvironment
  implements ApplicationEnvironmentType
{
  /**
   * An application environment based on the real system environment.
   */

  public ApplicationRealEnvironment()
  {

  }

  @Override
  public FileSystem filesystem()
  {
    return FileSystems.getDefault();
  }

  @Override
  public Optional<String> systemProperty(final String name)
  {
    return Optional.ofNullable(
      System.getProperty(Objects.requireNonNull(name, "name"))
    );
  }

  @Override
  public Optional<String> environmentVariable(final String name)
  {
    return Optional.ofNullable(
      System.getenv(Objects.requireNonNull(name, "name"))
    );
  }

  @Override
  public <S> Iterator<S> servicesFor(
    final Class<S> clazz)
  {
    return ServiceLoader.load(
      Objects.requireNonNull(clazz, "clazz")
    ).iterator();
  }
}
