# Map Renamer [CSGO]
Map Renamer is a software made by Keplyx to help mappers in renaming their maps and all necessary files.
It renames maps under the csgo directory, it will not rename the .vmf, so compile your map before trying to rename it.

**It doesn't rename packed files! Be sure to rename your map before packing files into it!**


**Why create this software?**

Because some maps can become complex and need a lot of files to work (eg: .kv for custom models, .cfg for custom rules, .dds for radars..., *see below for a list of all supported files*). While you can easily rename your map by saving your .vmf to a different name, then compiling, it will only rename the .bsp, not every other file. So those files will not work anymore with your renamed map, and you have to change the name of every needed file (sometimes the content of the file itself) to make them work again. This software helps you by renaming every file and its content related to your map, so you don't have to worry about that. It can also help you making backups as it can copy you map/files and rename that copy.

I'm in no way an expert programmer, so I hope you will like this software!


##FEATURES

- Multiplatform
- Find all files used for your map and rename them to your new map name
- Rename content of files (eg: .kv)
- Can rename a copy or overwrite original files
- You can choose which files you don't want to rename
- Easy to use

##How to run

- Linux users, run Start.sh
- Windows users, run Start.bat

**This software needs JAVA to run**, if you don't have it, [you can download it here](https://www.java.com/download)

##How to use

1. First, select your csgo folder. *eg: [steam directory]/Counter-Strike Global Offensive/csgo*
2. Then enter your old map name without .bsp, or select it in the file browser. *eg: de_dust2*
3. Click on Find, and a list of all found files will be displayed
4. If you don't want to rename some of the files, select them and click on Do not rename selected
5. You can choose to overwrite the original file, but this is not recommended (having backups is always good)
6. Enter your new map name without .bsp. *eg: de_dust3*
7. Finally, click on Rename! If you are renaming a copy of a complex map (like de_nuke) it can take some time


##Supported files

- maps/XXX.bsp
- maps/XXX.nav
- maps/XXX.kv
- maps/XXX_camera.txt
- maps/XXX_level_sounds.txt
- maps/cfg/XXX.cfg
- resource/overviews/XXX.txt
- resource/overviews/XXX_lower_radar.dds
- resource/overviews/XXX_higher_radar.dds
- resource/overviews/XXX_radar.dds
- resource/overviews/XXX_radar_spectate.dds
- scripts/soundscapes_XXX.txt


Software under the [GNU GPL 3 licence](https://www.gnu.org/licenses/gpl.html), available in LICENCE provided with the software.

Made by [Keplyx](http://steamcommunity.com/id/Keplyx/)

##Preview image
![map renamer](https://cloud.githubusercontent.com/assets/23726131/20607393/1a1e8188-b278-11e6-9ffb-fa66e02e66c5.png)
