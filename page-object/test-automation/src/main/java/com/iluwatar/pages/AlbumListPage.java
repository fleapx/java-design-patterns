/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.pages;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.iluwatar.Page;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Page Object encapsulating the Album List page (album-list.html)
 */
public class AlbumListPage extends Page {

  private static final String ALBUM_LIST_HTML_FILE = "album-list.html";
  private static final String PAGE_URL = "file:" + AUT_PATH + ALBUM_LIST_HTML_FILE;

  private HtmlPage page;

  private List<HtmlAnchor> albumLinks;


  /**
   * Constructor
   */
  public AlbumListPage(WebClient webClient) {
    super(webClient);
    try {
      page = this.webClient.getPage(PAGE_URL);

      // uses XPath to find list of html anchor tags with the class album in it
      albumLinks = (List<HtmlAnchor>) page.getByXPath("//tr[@class='album']//a");

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAt() {
    return "Album List".equals(page.getTitleText());
  }

  /**
   * Selects an album by the given album title
   *
   * @param albumTitle the title of the album to click
   * @return the album page
   */
  public AlbumPage selectAlbum(String albumTitle) {
    for (HtmlAnchor anchor : albumLinks) {
      if (anchor.getTextContent().equals(albumTitle)) {
        try {
          anchor.click();
          return new AlbumPage(webClient);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    throw new IllegalArgumentException("No links with the album title: " + albumTitle);
  }


}
