/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.portal.domain;

import com.marcelmika.lims.api.entity.GroupCollectionDetails;
import com.marcelmika.lims.api.entity.GroupDetails;
import com.marcelmika.lims.api.environment.Environment.BuddyListStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/3/14
 * Time: 6:10 PM
 */
public class GroupCollection {

    private List<Group> groups = Collections.synchronizedList(new ArrayList<Group>());
    private Date lastModified;
    private BuddyListStrategy listStrategy;
    private int etag;

    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Create new group and maps data from group details
     *
     * @return GroupCollection
     */
    public static GroupCollection fromGroupCollectionDetails(GroupCollectionDetails details) {
        // Create new group
        GroupCollection groupCollection = new GroupCollection();
        // Map data to group details
        groupCollection.lastModified = details.getLastModified();
        groupCollection.listStrategy = details.getListStrategy();

        if (details.getLastModified() != null) {
            // Etag is made of the last modification date and the list strategy type. Thus if the group collection
            // changes its modification date or its type the client will be forced to download updated version
            groupCollection.etag = details.getLastModified().hashCode() + groupCollection.listStrategy.hashCode();
        }

        // Relations
        if (details.getGroups() != null) {
            for (GroupDetails groupDetail : details.getGroups()) {
                groupCollection.groups.add(Group.fromGroupDetails(groupDetail));
            }
        }

        return groupCollection;
    }


    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public int getEtag() {
        return etag;
    }

    public void setEtag(int etag) {
        this.etag = etag;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public BuddyListStrategy getListStrategy() {
        return listStrategy;
    }

    public void setListStrategy(BuddyListStrategy listStrategy) {
        this.listStrategy = listStrategy;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
