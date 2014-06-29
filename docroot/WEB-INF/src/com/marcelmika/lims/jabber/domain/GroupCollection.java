package com.marcelmika.lims.jabber.domain;

import com.marcelmika.lims.api.entity.GroupCollectionDetails;
import com.marcelmika.lims.api.entity.GroupDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/3/14
 * Time: 5:29 PM
 */
public class GroupCollection {

    private Date lastModified = new Date();
    private List<Group> groups = Collections.synchronizedList(new ArrayList<Group>());

    /**
     * Maps a list of groups to a list of group details
     *
     * @return list of group details
     */
    public GroupCollectionDetails toGroupCollectionDetails() {
        // Create new collection
        GroupCollectionDetails details = new GroupCollectionDetails();
        // Groups
        List<GroupDetails> groups = new ArrayList<GroupDetails>();
        for(Group group : this.groups) {
            groups.add(group.toGroupDetails());
        }
        details.setGroups(groups);

        // Modification date
        details.setLastModified(lastModified);

        return details;
    }


    public void addGroups(List<Group> groups) {
        // Overwrite current groups
        this.groups.clear();
        this.groups.addAll(groups);
        // Update modification date
        this.lastModified = new Date();
    }

    public Date getLastModified() {
        return lastModified;
    }
}