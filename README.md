# MediaTool

[Download last JAR version](https://github.com/jenasnic/media-tool/raw/refs/heads/master/download/mediaTool.jar)

## Music renamer

Tool to rename music when tagging file from filename:

* add prefix for artist/album
* add suffix for additional info
* remove prefix
* remove specific text
* replace text using regular expression

## Picture renamer

Tool to rename picture using EXIF metadata.

Format is `yyyyMMdd_HHmmss` (year, month, day and time).

## Picture synchronizer

Allows to synchronize 2 folders to keep only pictures with same name (useful when a same picture exist with 2 extensions as JPG and RAW).

## Configuration file

A configuration file `config.ini' is stored in same folder as current JAR file.

This file contains following keys:

* `active.tab` default active tab (`0` for music renamer, `1` for picture renamer or `2` for picture synchronizer)
* `music.extensions` music file extensions to take into account separated with `;` (default to `mp3;flac;wma`)
* `music.folder.rename` last folder path used to rename music
* `picture.extensions` picture file extensions to take into account separated with `;` (default to `jpg;jpeg;raw;nef;mts;mp4`)
* `picture.folder.rename` last folder path used to rename pictures
* `picture.folder.synchronize` last folder path used to synchronize pictures

## Docker

Generate JAR file with following command using docker :

```bash
docker-compose run --rm -w /app java mvn clean package
```
