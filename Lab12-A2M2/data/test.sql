SELECT PET.pet_name, OWNER.first_name,owner.last_name
FROM PET JOIN OWNER ON PET.pet_owner = OWNER.owner_id;
