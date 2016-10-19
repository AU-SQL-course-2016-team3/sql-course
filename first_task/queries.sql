-- 1. 
SELECT C.name
FROM Sportsman S JOIN SportsmanEvents E ON S.id = E.sportsman_id
JOIN Country C ON C.id = S.country_id
WHERE E.medal IS NOT NULL
GROUP BY C.name;

-- 2. 
SELECT M.name
FROM (
  SELECT C.name, COUNT(E.medal = 'gold') AS gold_cnt
  FROM Country C LEFT JOIN Sportsman S ON C.id = S.country_id
  LEFT JOIN SportsmanEvents E ON S.id = E.sportsman_id
  GROUP BY C.id
) AS M
WHERE M.gold_cnt = 0;

-- 3.
CREATE VIEW L AS (
  SELECT C.id, C.address, C.name, H.name as head_name, H.phone, Country.name as country_name
  FROM Head H JOIN Construction C ON H.headquarters_id = C.id
  JOIN Country ON H.country_id = Country.id
);

SELECT L.address, L.name, L.head_name, L.phone, L.country_name
FROM L
WHERE L.id in (SELECT L.id from L GROUP BY L.id HAVING count(*) > 1)
ORDER BY L.address;

DROP VIEW L;

-- 4. 
SELECT C.name, SUM(case S.id is NULL when true then 0 else 1 end)
FROM Country C LEFT JOIN Sportsman S ON S.country_id = C.id
GROUP BY C.id;

-- 5.
CREATE VIEW R AS (
  SELECT S.id, S.name, count(*) as cnt
  FROM Sport S LEFT JOIN SportsmanSports SS ON S.id = SS.sport_id
  GROUP BY S.id
);

SELECT *
FROM R
WHERE R.cnt IN
((SELECT MIN(cnt) FROM R) UNION ALL (SELECT MAX(cnt) FROM R));

DROP VIEW R;


-- 7. 
CREATE VIEW Events AS (
  WITH EventNum AS (
    SELECT E.id, E.event_date, E.sport_id, E.construction_id, SUM(case S.id is NULL when true then 0 else 1 end) as cnt
    FROM Event E LEFT JOIN SportsmanEvents SE ON SE.event_id = E.id
    LEFT JOIN Sportsman S ON S.id = SE.sportsman_id
    GROUP BY E.id
  )
  SELECT C.id, C.address, C.name, S.name as sport, E.event_date, cnt
  FROM Construction C JOIN EventNum E ON E.construction_id = C.id
  LEFT JOIN Sport S ON S.id = E.sport_id
);


SELECT *
FROM ((
  SELECT address, name, sport, event_date, cnt, 'max' as type
  FROM (
    SELECT id, address, name, sport, event_date, cnt, MAX(cnt) OVER (PARTITION BY id) as max
    FROM Events
  ) as S
  WHERE cnt = S.max
) UNION ALL (
  SELECT address, name, sport, event_date, cnt, 'min' as type
  FROM (
    SELECT id, address, name, sport, event_date, cnt, MIN(cnt) OVER (PARTITION BY id) as min
    FROM Events
  ) as S
  WHERE cnt = S.min
)) as Q
ORDER BY address;

DROP VIEW Events;

-- 8.
SELECT C.name, COUNT(E.medal = 'gold') AS gold_cnt, COUNT(E.medal = 'silver') AS silver_cnt, COUNT(E.medal = 'bronze') AS bronze_cnt
FROM Country C LEFT JOIN Sportsman S ON C.id = S.country_id
LEFT JOIN SportsmanEvents E ON S.id = E.sportsman_id
GROUP BY C.id
ORDER BY gold_cnt DESC, silver_cnt DESC, bronze_cnt DESC;

-- 10.
WITH Count AS (
  SELECT C.name, COUNT(*) as cnt
  FROM Sportsman S JOIN Country C ON S.country_id = C.id
  GROUP BY C.id
),
PopSport AS (
  SELECT F.name, MAX(cnt) as cnt
  FROM (
    SELECT C.name, SS.sport_id, COUNT(*) as cnt
    FROM Sportsman S JOIN Country C ON S.country_id = C.id
    JOIN SportsmanSports SS ON SS.sportsman_id = S.id
    GROUP BY C.name, SS.sport_id
  ) AS F
  GROUP BY F.name
)
SELECT PS.name
FROM PopSport PS JOIN Count C ON PS.name = C.name
WHERE (C.cnt > 2) AND (2 * PS.cnt >= C.cnt);
