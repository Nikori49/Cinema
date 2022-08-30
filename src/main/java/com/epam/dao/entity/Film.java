package com.epam.dao.entity;

import com.epam.dao.DBManager;

/**
 * Represents entry from DB table <code>films</code> .
 * <p>
 * Has the following fields:
 * <ul>
 *     <li><code>Long id</code></li>
 *     <li><code>String name</code></li>
 *     <li><code>String description</code></li>
 *     <li><code>String genre</code></li>
 *     <li><code>String posterImgPath</code>: relative path to poster image saved in <code>posterImages</code> package</li>
 *     <li><code>Long runningTime</code>: specified in minutes</li>
 *     <li><code>String youtubeTrailerId</code>: YouTube id of the films trailer</li>
 * </ul>
 * Setter and getter methods are provided.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 * @see DBManager
 */
public class Film {
    Long id;
    String name;
    String description;
    String genre;
    String posterImgPath;
    String director;
    Long runningTime;
    String youtubeTrailerId;

    public Film() {
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", posterImgPath='" + posterImgPath + '\'' +
                ", director='" + director + '\'' +
                ", runningTime=" + runningTime +
                ", youtubeTrailerId='" + youtubeTrailerId + '\'' +
                '}';
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPosterImgPath() {
        return posterImgPath;
    }

    public void setPosterImgPath(String posterImgPath) {
        this.posterImgPath = posterImgPath;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Long getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(Long runningTime) {
        this.runningTime = runningTime;
    }

    public String getYoutubeTrailerId() {
        return youtubeTrailerId;
    }

    public void setYoutubeTrailerId(String youtubeTrailerId) {
        this.youtubeTrailerId = youtubeTrailerId;
    }
}
