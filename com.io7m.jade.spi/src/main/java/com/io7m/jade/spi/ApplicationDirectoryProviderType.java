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

import java.nio.file.Path;

/**
 * A provider of application directories.
 */

public interface ApplicationDirectoryProviderType
{
  /**
   * Initialize the provider, returning {@code true} if the provider is
   * applicable to the current application environment and operating system.
   *
   * @param context     The context
   * @param environment The environment
   *
   * @return {@code true} if this provider is applicable
   */

  boolean initialize(
    ApplicationProviderContextType context,
    ApplicationEnvironmentType environment);

  /**
   * @return A single base directory relative to which user-specific configuration files should be written.
   */

  Path configurationDirectory();

  /**
   * @return The directory that contains data files
   */

  Path dataDirectory();

  /**
   * @return A single base directory relative to which user-specific non-essential (cached) data should be written.
   */

  Path cacheDirectory();
}
