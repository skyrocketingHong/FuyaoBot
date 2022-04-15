

ALTER TABLE `group_fishing`
  ADD CONSTRAINT `group_fishing_fk1` FOREIGN KEY (`fish_1`) REFERENCES `game_fishing` (`fish_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `group_fishing_fk2` FOREIGN KEY (`fish_2`) REFERENCES `game_fishing` (`fish_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `group_fishing_fk3` FOREIGN KEY (`fish_3`) REFERENCES `game_fishing` (`fish_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `group_fishing_fk4` FOREIGN KEY (`fish_4`) REFERENCES `game_fishing` (`fish_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `group_fishing_fk5` FOREIGN KEY (`fish_5`) REFERENCES `game_fishing` (`fish_id`) ON DELETE SET NULL;
