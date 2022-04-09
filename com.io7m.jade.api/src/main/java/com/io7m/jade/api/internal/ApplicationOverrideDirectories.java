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

import com.io7m.jade.spi.ApplicationDirectoryProviderType;
import com.io7m.jade.spi.ApplicationEnvironmentType;
import com.io7m.jade.spi.ApplicationProviderContextType;

import java.nio.file.Path;
import java.util.Objects;

/**
 * A set of directories that override the default selection.
 */

public final class ApplicationOverrideDirectories
  implements ApplicationDirectoryProviderType
{
  private final Path baseDirectory;
  private Path configurationDirectory;
  private Path dataDirectory;
  private Path cacheDirectory;

  /**
   * A set of directories that override the default selection.
   *
   * @param inBaseDirectory The base directory
   */

  public ApplicationOverrideDirectories(
    final Path inBaseDirectory)
  {
    this.baseDirectory =
      Objects.requireNonNull(inBaseDirectory, "baseDirectory");
  }

  @Override
  public boolean initialize(
    final ApplicationProviderContextType configuration,
    final ApplicationEnvironmentType environment)
  {
    Objects.requireNonNull(configuration, "configuration");
    Objects.requireNonNull(environment, "environment");

    this.configurationDirectory =
      this.baseDirectory.resolve("config")
        .toAbsolutePath();

    this.dataDirectory =
      this.baseDirectory.resolve("data")
        .toAbsolutePath();

    this.cacheDirectory =
      this.baseDirectory.resolve("cache")
        .toAbsolutePath();

    return true;
  }

  @Override
  public Path configurationDirectory()
  {
    return this.configurationDirectory;
  }

  @Override
  public Path dataDirectory()
  {
    return this.dataDirectory;
  }

  @Override
  public Path cacheDirectory()
  {
    return this.cacheDirectory;
  }
}
