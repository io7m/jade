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

package com.io7m.jade.api;

import java.nio.file.Path;

/**
 * The directories available to the application.
 *
 * The interface exposes something that aims to vaguely represent the conventions
 * defined by the XDG Base Directory specification. Sensible equivalents are
 * chosen for operating systems that do not purport to conform to the XDG
 * standards.
 *
 * @see "https://specifications.freedesktop.org/basedir-spec/basedir-spec-latest.html"
 */

public interface ApplicationDirectoriesType
{
  /**
   * The directory to be used for configuration files.
   *
   * @return The directory to be used for user-specific configuration files
   */

  Path configurationDirectory();

  /**
   * The directory to be used for user-specific data files.
   *
   * @return The directory to be used for user-specific data files
   */

  Path dataDirectory();

  /**
   * The directory to be used for user-specific non-essential (cached) data.
   *
   * @return The directory to be used for user-specific non-essential (cached) data.
   */

  Path cacheDirectory();
}
