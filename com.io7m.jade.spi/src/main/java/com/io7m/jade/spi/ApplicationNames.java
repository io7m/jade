/*
 * Copyright © 2020 Mark Raynsford <code@io7m.com> http://io7m.com
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

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Application names.
 */

public final class ApplicationNames
{
  private ApplicationNames()
  {

  }

  /**
   * The pattern that defines a valid application name.
   */

  public static final Pattern VALID_NAMES =
    Pattern.compile("[\\p{Alnum}_\\.]+", Pattern.UNICODE_CHARACTER_CLASS);

  /**
   * Check that the given name is a valid application name.
   *
   * @param name The application name
   */

  public static void checkValid(
    final String name)
  {
    if (!isValid(name)) {
      throw new IllegalArgumentException(
        String.format(
          "Application name '%s' is not valid; must match '%s'",
          name,
          VALID_NAMES
        )
      );
    }
  }

  /**
   * Check that the given name is a valid application name.
   *
   * @param name The application name
   *
   * @return {@code true} iff the name is valid
   */

  public static boolean isValid(
    final String name)
  {
    return VALID_NAMES.matcher(
      Objects.requireNonNull(name, "name")
    ).matches();
  }
}
